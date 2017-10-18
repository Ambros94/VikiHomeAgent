# VikiHomeAgent

This project has been develped as a Bachelor Thesis in Computer Science at SUPSI (Scuola Universitaria Professionale Della Svizzera Italiana)

It is a component of a wider architecture that automatically detects IoT devices in your home, trough a middleware.
Devices and actions related to them are automatically inferred trough reflection and they can me modifier to proper annotation.

This modules does intent detection and intent extraction, mapping sentences to possibile command that are then executed in the home.

It also allows a small personalization degree trough command learning. 
When a command is not recognized a it can be mapped into a pre-recognized one, improving system capabilities to interact with user in a personal way.
New commands can also be taught to the system ("goodnight" -> "shut down everything")
