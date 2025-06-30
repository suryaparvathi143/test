pipeline {
    agent any

    environment {
        EC2_USER = 'ec2-user'
        EC2_HOST = '3.22.52.143'
        REMOTE_DIR = '/home/ec2-user/springboot-app'
        APP_NAME = 'test-0.0.1-SNAPSHOT.jar'  // ✅ Correct name
        SSH_KEY = 'ec2-ssh-key'               // Jenkins SSH Credentials ID
    }

    stages {
        stage('Clone Repository') {
            steps {
                echo "Cloning the repository..."
                git branch: 'main', url: 'https://github.com/suryaparvathi143/test.git'
            }
        }

		stage('Build with Maven') {
		    steps {
		        echo "Running Maven build..."
		        sh '/opt/homebrew/bin/mvn clean package -DskipTests'
		    }
		}

        stage('Deploy to EC2') {
            steps {
                echo "Deploying to EC2..."
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
