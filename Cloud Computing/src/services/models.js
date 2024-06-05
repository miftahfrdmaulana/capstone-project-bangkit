const tf = require('@tensorflow/tfjs-node');
const path = require('path');

class L2 {
    static className = 'L2';

    constructor(config) {
        return tf.regularizers.l1l2(config);
    }
}

class Model {
    static instance;
    models;

    constructor() {
        tf.serialization.registerClass(L2);
        this.models = new Map();
    }

    static getInstance() {
        if (!Model.instance) {
            Model.instance = new Model();
        }
        return Model.instance;
    }

    async loadModel(modelPath) {
        modelPath = modelPath.trim().replace(/\\/g, '/');
        const modelName = modelPath.replace(/[^a-zA-Z0-9]/g, '_');

        const isLocal = !modelPath.startsWith('http');
        if (isLocal) {
            modelPath = path.resolve(__dirname, modelPath);
        }

        let model = this.models.get(modelName);
        if (model) {
            return model;
        }

        model = await tf.loadLayersModel(isLocal ? `file://${modelPath}` : modelPath);
        this.models.set(modelName, model);

        return model;
    }

    static async predictImage(imageBuffer, modelPath, plant, labels) {
        const model = await Model.getInstance().loadModel(modelPath);
        const result = tf.tidy(() => {
            let imgTensor = tf.node.decodeImage(imageBuffer);
            imgTensor = tf.image.resizeBilinear(imgTensor, [150, 150]);
            imgTensor = imgTensor.toFloat().div(255.0);
            imgTensor = imgTensor.expandDims(0);
            const prediction = model.predict(imgTensor);
            const values = prediction.arraySync();

            return values.flat();
        });

        const max = Math.max(...result);
        const index = result.indexOf(max);
        return { plant, label: labels[index], confidence: max.toFixed(3) };
    }
}

module.exports = { Model };
