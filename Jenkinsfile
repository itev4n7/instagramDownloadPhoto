node('node'){
    stage('git'){
        git 'https://github.com/itev4n7/instagramDownloadPhoto.git'
    }
    try{
        stage('Compile Stage'){
            withMaven(maven :'maven 3.6.3') {
                sh 'mvn clean compile'
            }
        }
    } catch (err) {
        echo "Caught: ${err}"
    }
    try{
        stage ('Testing Stage'){
            withMaven(maven :'maven 3.6.3') {
                sh 'mvn test'
            }
        }
    } catch (err) {
        echo "Caught: ${err}"
        currentBuild.result = 'FAILURE'
    }
}