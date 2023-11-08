echo "Building..."
docker build -t mswinarski/perfect-one:latest -t mswinarski/perfect-one:$(git log -1 --pretty=%h) .
echo "Pushing..."
docker push mswinarski/perfect-one:$(git log -1 --pretty=%h)
echo "Done. Now update perfect-one-pod.yaml to deploy the new image: mswinarski/perfect-one:$(git log -1 --pretty=%h)"