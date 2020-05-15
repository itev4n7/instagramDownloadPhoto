node('node'){
    stage('git'){
        git 'https://github.com/itev4n7/instagramDownloadPhoto.git'
    }
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
            mail bcc: '',
            body: "<b>Example</b><br>Project: ${env.JOB_NAME} <br>Build Number: ${env.BUILD_NUMBER} <br> URL de build: ${env.BUILD_URL}",
            charset: 'UTF-8',
            mimeType: 'text/html',
            subject: "ERROR CI: Project name -> ${env.JOB_NAME}",
            to: 'loha.malyoitar@gmail.com';
}