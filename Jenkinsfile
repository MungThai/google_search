pipeline {
    agent any
    tools {
        maven 'Maven 3.6.3'
        jdk 'jdk11'
    }
    stages {
        stage('Initialize') {
            steps {
                
                    echo "PATH = %PATH%"
                    echo "M2_HOME = %M2_HOME%"
                
            }
        }
        stage('Checkout') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/develop']], extensions: [], userRemoteConfigs: [[credentialsId: 'GitHub', url: 'https://github.com/MungThai/google_search']]])
            }
        }
        stage('Execute') {
            steps {
               bat 'mvn clean -Dtest=com.google.web.search.runners.JunitRunner test'
            }
            post {
                always {
                    step([$class: 'JUnitResultArchiver', testResults: '**/reports/junit/*.xml', healthScaleFactor: 1.0])
                    publishHTML (target: [
                            allowMissing: false,
                            alwaysLinkToLastBuild: false,
                            keepAll: true,
                            reportDir: 'coverage',
                            reportFiles: 'index.html',
                            reportName: "Junit Report"
                    ])
                    junit 'target/surefire-reports/**/*.xml'
                }
            }
        }
    }
}

