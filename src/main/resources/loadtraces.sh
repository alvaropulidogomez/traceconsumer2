while IFS= read -r line; do
curl -H "Content-Type: application/json" -X POST --data-binary "$line" http://localhost:8090/traces;
done < traces.json
