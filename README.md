# Generate public/private key pair

There is a test in `src/test/kotlin/com/comsysto/cycling/CreatePublicPrivateKeyPair.kt` to generate a public/private RSA key pair in the root folder of the repository.

Supply the `public.key` to your clients and keep your `private.key` secret. For production deployments, `private.key` should be copied to `src/main/resources`.
