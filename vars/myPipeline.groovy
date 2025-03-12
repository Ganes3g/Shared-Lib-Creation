def call() {
    pipeline {
        agent any
        stages {
            stage ('Git checkout'){
                steps {
                    script {
                        git branch: 'master', url:'https://github.com/Ganes3g/Shared-Lib-Creation.git'
                    }
                }
            }
            stage ('Maven Build') {
                steps {
                    script {
                        sh 'npm install'
                    }
                }
            }
            stage ('Sonarcube analysis') {
                steps {
                    script {
                        def scannerHome = tool 'sonarscanner4'
                        withSonarQubeEnv ('sonar-pro')
                        sh "${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=node.js-app"
                    }
                }
            }
            stage ('Docker build') {
                steps {
                    script {
                        sh 'docker build -t ganeshimage .'
                        sh 'docker images'
                    }
                }
            }
        }
    }
}
