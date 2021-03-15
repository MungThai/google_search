pipeline {
    agent any
    tools {
        maven 'Maven 3.6.3'
        jdk 'jdk11'
    }
    stages {
        stage('Initialize') {
            steps {
                sh '''
                    echo "PATH = $PATH"
                    echo "M2_HOME = $M2_HOME"
                '''
            }
        }
        stage('Checkout') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/develop']], extensions: [], userRemoteConfigs: [[credentialsId: 'GitHub', url: 'https://github.com/MungThai/google_search']]])
            }
        }
        stage('Test') {
            steps {
               //bat 'mvn clean -DfailIfNoTests=false -Dtest=com.google.web.search.runners.JunitRunner test'
               bat 'mvn clean -Dtest=com.google.web.search.runners.JunitRunner test'
            }
            post {
                always {
                   // step([$class: 'JUnitResultArchiver', testResults: '**/reports/junit/*.xml', healthScaleFactor: 1.0])
                    publishHTML (target: [
                            allowMissing: false,
                            alwaysLinkToLastBuild: false,
                            keepAll: true,
                        reportDir: '${BUILD_URL}/jobs/${JOB_NAME}/htmlreports/HTML_20Report',
                            reportFiles: 'cucumber.html',
                            reportName: "HTML Report"
                    ])
                    script {
                        junit 'target/surefire-reports/**/*.xml'
                                                
                    }
                }
            }
        }
    }
    post {
        always {
          echo "Send notifications for result: ${currentBuild.result}"
        }
    }    
}

