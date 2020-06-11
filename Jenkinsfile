node('jenNode'){
    try{
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
                sh 'mvn test -Dsurefire.suiteXmlFiles=TestNG.xml '
            }
        }
        mail bcc: '',
        body: "<b>Example</b><br>Project: ${env.JOB_NAME} <br>Build Number: ${env.BUILD_NUMBER} <br> URL de build: ${env.BUILD_URL}",
        cc: '',
        charset: 'UTF-8',
        from: '',
        mimeType: 'text/html',
        replyTo: '',
        subject: "CI build success: Project name -> ${env.JOB_NAME}",
        to: 'loha.malyoitar@gmail.com';
    } catch(e){
        mail bcc: '',
        body: "<b>Example</b><br>Project: ${env.JOB_NAME} <br>Build Number: ${env.BUILD_NUMBER} <br> URL de build: ${env.BUILD_URL}",
        cc: '',
        charset: 'UTF-8',
        from: '',
        mimeType: 'text/html',
        replyTo: '',
        subject: "CI build failure: Project name -> ${env.JOB_NAME}",
        to: 'loha.malyoitar@gmail.com';

        throw err
    }
}