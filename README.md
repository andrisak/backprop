# backprop
Implementation of a back propagation algorithm.

# example request
default local url: http://localhost:9000/api/classify

```javascript
{
    "classificationInput": {
        "trainingData": [
            {
                "firstInput": 1.0,
                "secondInput": 1.0,
                "target": 0.0
            },
            {
                "firstInput": 1.0,
                "secondInput": 0.0,
                "target": 1.0
            },
            {
                "firstInput": 0.0,
                "secondInput": 1.0,
                "target": 1.0
            },
            {
                "firstInput": 0.0,
                "secondInput": 0.0,
                "target": 0.0
            }],
        "valuesToClassify": {
            "firstInput": 0.0,
            "secondInput": 0.0,
            "iterations": 5000
        }
    }
}
```
