echo "Building..."
docker build -t mswinarski/perfect-two:latest -t mswinarski/perfect-two:$(git log -1 --pretty=%h) .
echo "Pushing..."
docker push mswinarski/perfect-two:$(git log -1 --pretty=%h)
echo "Done. Now update perfect-two-pod.yaml to deploy the new image: mswinarski/perfect-two:$(git log -1 --pretty=%h)"