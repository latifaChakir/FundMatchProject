pipeline {
    agent any

    tools {
        maven 'Maven'
        git 'Git'
    }

    environment {
        SPRING_DATASOURCE_URL = 'jdbc:postgresql://localhost:5432/fundmatch'
        DOCKER_IMAGE = 'fundmatch-system'
        DOCKER_TAG = "${BUILD_NUMBER}"
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    deleteDir()
                    echo "Clonage du dépôt Git..."
                    bat '''
                        git clone https://github.com/latifaChakir/FundMatchProject .
                        echo "Dépôt cloné avec succès."
                    '''
                }
            }
        }

        stage('Environment Check') {
            steps {
                bat '''
                    echo "Version de Git :"
                    git --version
                    echo "Branche Git actuelle :"
                    git branch --show-current
                    echo "Statut de Git :"
                    git status
                    echo "Version de Java :"
                    java -version
                    echo "Version de Javac :"
                    javac -version
                    echo "Contenu du répertoire de travail :"
                    cd
                    dir
                '''
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean package'
            }
        }

        stage('Unit Tests') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${DOCKER_IMAGE}:${DOCKER_TAG}")
                    docker.build("${DOCKER_IMAGE}:latest")
                }
            }
        }

        stage('Manual Approval') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    input message: 'Déployer en production ?', ok: 'Procéder'
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    docker.image("${DOCKER_IMAGE}:${DOCKER_TAG}").run('-p 8082:8080')
                }
            }
        }
    }

    post {
        success {
            mail to: 'chakirlatifa2001@gmail.com',
                 subject: "Pipeline Success - FundMatchProject",
                 body: "Le pipeline Jenkins s'est terminé avec succès !"
        }
        failure {
            mail to: 'chakirlatifa2001@gmail.com',
                 subject: "Pipeline Failure - FundMatchProject",
                 body: "Le pipeline Jenkins a échoué. Veuillez vérifier les logs."
        }
    }
}
