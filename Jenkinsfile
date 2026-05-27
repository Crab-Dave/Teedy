pipeline {
    agent any

    environment {
        DEPLOYMENT_NAME = "hello-node"
        IMAGE_NAME = "sismics/docs:v1.11"
    }

    stages {
        stage('Start Minikube') {
            steps {
                sh '''
                    if ! minikube status | grep -q "Running"; then
                        echo "Starting Minikube..."
                        minikube start
                    else
                        echo "Minikube already running."
                    fi
                '''
            }
        }

        stage('Set Image') {
            steps {
                sh '''
                    echo "Setting image for deployment..."
                    CONTAINER_NAME=$(kubectl get deployment "${DEPLOYMENT_NAME}" -o jsonpath='{.spec.template.spec.containers[0].name}')
                    if [ -z "${CONTAINER_NAME}" ]; then
                        echo "Failed to detect container name for deployment ${DEPLOYMENT_NAME}."
                        exit 1
                    fi
                    echo "Using container name: ${CONTAINER_NAME}"
                    kubectl set image deployment/${DEPLOYMENT_NAME} ${CONTAINER_NAME}=${IMAGE_NAME}
                '''
            }
        }

        stage('Verify') {
            steps {
                sh 'kubectl rollout status deployment/${DEPLOYMENT_NAME}'
                sh 'kubectl get pods'
            }
        }
    }
}
