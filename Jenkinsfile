pipeline {
    agent any

    stages {
        stage('Compile Stage'){
            steps{
                withMaven(maven :'maven 3.6.3') {
                    sh 'mvn clean compile'
                }
            }
        }
        stage ('Testing Stage'){
            steps{
                withMaven(maven :'maven 3.6.3') {
                    sh 'mvn test'
                }
            }
        }
        stage ('HTML Report Stage'){
            steps{
                withMaven(maven :'maven 3.6.3') {
                    sh 'mvn com.example:blobtohtml-maven-plugin:1.0-SNAPSHOT:blob-to-html'
                }
            }
        }
        stage ('Deploy Stage'){
            steps{
                withMaven(maven :'maven 3.6.3') {
                    sh 'mvn deploy'
                }
            }
        }
    }
}