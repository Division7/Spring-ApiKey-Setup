## Spring-ApiKey-Setup
This is a simple kotlin-based spring boot application demonstrating how to use a unified API key/OIDC
authentication mechanism.

Notably, it has no tests. 

If you are, say, a [ucsb-cs156](https://github.com/ucsb-cs156) student, you should probably notice that
the filter creates a whacky interaction if you're a user who is already signed in and attempts to use
your API key. I would certainly consider this a bug.

On top of that, these API keys are very simple, and have no expiration, permissions management, no attribution to users, etc.

You may want to use this as a starting point for your own API key/OIDC authentication mechanism. You also may want to consider being able to pass in a User to the `ApiKeyToken` constructor, and have the key inherit their permissions (if you were, say, pulling from a users table).

Additionally, if you were a cs156 student, I would tell you to consider whether or not you should add some noop `MockitoBeans` for anything that is passed to the Filter so that the entire testing suite doesn't go insane.

Good luck!
