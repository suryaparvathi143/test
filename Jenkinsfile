pipeline {
    agent any

    environment {
        EC2_USER = 'ec2-user'
        EC2_HOST = '3.15.195.208'
        REMOTE_DIR = '/home/ec2-user/springboot-app'
        APP_NAME = 'test.jar'
        SSH_KEY = 'ec2-ssh-key' // Jenkins Credentials ID
    }

    stages {
        stage('Clone Repository') {
            steps {
                git 'https://github.com/suryaparvathi143/test.git'
            }
        }

        stage('Build with Maven') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Deploy to EC2') {
            steps {
                sshPublisher(
                    publishers: [
                        sshPublisherDesc(
                            configName: "${SSH_KEY}",
                            transfers: [
                                sshTransfer(
                                    sourceFiles: "target/${APP_NAME}",
                                    removePrefix: "target",
                                    remoteDirectory: "${REMOTE_DIR}",
                                    execCommand: """
                                        pkill -f ${APP_NAME} || true
                                        nohup java -jar ${REMOTE_DIR}/${APP_NAME} > ${REMOTE_DIR}/app.log 2>&1 &
                                    """
                                )
                            ],
                            verbose: true
                        )
                    ]
                )
            }
        }
    }
}
