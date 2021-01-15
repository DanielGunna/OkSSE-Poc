data='{"counter":"'"$1"'"}'
curl -X POST \
 -H "Content-Type: application/json" \
 -d $data \
 -s http://localhost:3000/nest

echo "\n"
