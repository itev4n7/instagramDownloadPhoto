node('node'){
    stage('Compile Stage'){
        withMaven(maven :'maven 3.6.3') {
            sh 'mvn clean compile'
        }
    }
    stage ('Testing Stage'){
        withMaven(maven :'maven 3.6.3') {
            sh 'mvn test'
        }
    }
}