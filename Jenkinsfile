pipeline {
    agent any

    environment {
        DEPLOYMENT_NAME = "hello-node"
        CONTAINER_NAME = "minikube"
        IMAGE_NAME = "crabdave987/teedy:latest"
    }

    stages {
        stage('Start Minikube') {
            steps {
                checkout scmGit(
                  branches: [[name: '*/b12311707']],
                  extensions: [],
                  userRemoteConfigs: [[url: 'https://github.com/Crab-Dave/Teedy.git']]
                )
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