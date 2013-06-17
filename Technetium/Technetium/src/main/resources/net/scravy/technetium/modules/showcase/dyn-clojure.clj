(let [root (.createElement response "clojure")]
	(.append response root)
	(doto response
		(.setTemplate "showcase-dyn")
		(.createElement root "says-hello" "Hello from Clojure")
	)
)
 