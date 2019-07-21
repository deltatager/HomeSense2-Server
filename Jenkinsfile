pipeline {
    agent any
    stages {
        stage('Build') { 
            steps {
                sh 'whoami'
                sh 'mvn -B -DskipTests clean package' 
            }
        }
    }
}
