pipeline {
  agent any

  options {
    skipDefaultCheckout(true)
  }

  environment {
    // Jenkins credentials ID for Docker Hub username/password or token.
    DOCKER_HUB_CREDENTIALS = 'dockerhub_credentials'

    DOCKER_IMAGE = 'crabdave987/teedy'
    DOCKER_TAG = "${env.BUILD_NUMBER}"
    CONTAINER_NAME = 'teedy-container-8081'
  }

  stages {
    stage('Docker access') {
      steps {
        sh 'docker version'
      }
    }

    stage('Build') {
      steps {
        checkout scmGit(
          branches: [[name: '*/b12311707']],
          extensions: [],
          userRemoteConfigs: [[url: 'https://github.com/Crab-Dave/Teedy.git']]
        )

        sh 'mvn -B -DskipTests clean package'
      }
    }

    stage('Building image') {
      steps {
        script {
          docker.build("${env.DOCKER_IMAGE}:${env.DOCKER_TAG}")
        }
      }
    }

    stage('Upload image') {
      steps {
        script {
          docker.withRegistry('https://index.docker.io/v1/', env.DOCKER_HUB_CREDENTIALS) {
            docker.image("${env.DOCKER_IMAGE}:${env.DOCKER_TAG}").push()
            docker.image("${env.DOCKER_IMAGE}:${env.DOCKER_TAG}").push('latest')
          }
        }
      }
    }

    stage('Run containers') {
      steps {
        script {
          sh "docker stop ${env.CONTAINER_NAME} || true"
          sh "docker rm ${env.CONTAINER_NAME} || true"

          docker.image("${env.DOCKER_IMAGE}:${env.DOCKER_TAG}").run(
            "--name ${env.CONTAINER_NAME} -d -p 8081:8080"
          )

          sh 'docker ps --filter "name=teedy-container"'
        }
      }
    }
  }

  post {
    always {
      archiveArtifacts artifacts: '**/target/**/*.jar', fingerprint: true, allowEmptyArchive: true
      archiveArtifacts artifacts: '**/target/**/*.war', fingerprint: true, allowEmptyArchive: true
    }
  }
}
