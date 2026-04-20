## Spring-ApiKey-Setup
This is a simple kotlin-based spring boot application demonstrating how to use a unified API key/OIDC
authentication mechanism.

Notably, it has no tests. 

If you are, say, a [ucsb-cs156](https://github.com/ucsb-cs156) student, you should probably notice that
the filter creates a whacky interaction if you're a user who is already signed in and attempts to use
your API key. I would certainly consider this a bug.

On top of that, these API keys are very simple, and have no expiration, permissions management, no attribution to users, etc.

You may want to use this as a starting point for your own API key/OIDC authentication mechanism.

Good luck!