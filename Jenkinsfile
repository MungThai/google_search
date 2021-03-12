pipeline {
    agent any
    tools {
        maven 'Maven 3.6.3'
        jdk 'openjdk-11'
    }
    stages {
        stage('Initialize') {
            steps {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                '''
            }
        }
        stage('Checkout') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/develop']], extensions: [], userRemoteConfigs: [[credentialsId: 'GitHub', url: 'https://github.com/MungThai/google_search']]])
            }
        }
        stage('Execute') {
            steps {
                sh 'mvn clean -Dtest=com.google.web.search.runners.JunitRunner test'
            }
            post {
                always {
                    junit 'target/surefire-reports/**/*.xml'
                }
            }
        }
    }
}

