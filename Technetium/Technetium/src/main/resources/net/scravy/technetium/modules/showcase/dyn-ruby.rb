$response.setTemplate "showcase-dyn"

root = $response.createElement "ruby"
$response.createElement root, "says-hello", "Hello, says Ruby"

$response.append root
