# Contributing to Player Safe Login

Thank you for your interest in contributing to Player Safe Login! This document provides guidelines and instructions for contributing.

## Code of Conduct

Be respectful, inclusive, and professional. We're all here to improve this project together.

## Getting Started

### Prerequisites
- Java 21 or higher
- Gradle (included via gradlew)
- Git

### Setup Development Environment

1. **Fork and clone the repository**
   ```bash
   git clone https://github.com/YOUR_USERNAME/player-safe-login.git
   cd player-safe-login
   ```

2. **Set up your IDE**
   ```bash
   # For IntelliJ IDEA
   ./gradlew idea
   
   # For Eclipse
   ./gradlew eclipse
   ```

3. **Build the project**
   ```bash
   ./gradlew build
   ```

4. **Run in development**
   ```bash
   # Run client
   ./gradlew runClient
   
   # Run server
   ./gradlew runServer
   ```

## Development Workflow

### Creating a Branch
Create a feature branch from `develop`:
```bash
git checkout develop
git pull origin develop
git checkout -b feature/your-feature-name
```

Use descriptive branch names:
- `feature/` - New features
- `bugfix/` - Bug fixes
- `docs/` - Documentation updates
- `refactor/` - Code refactoring

### Commit Guidelines
Follow conventional commit format:
```
<type>(<scope>): <subject>

<body>

<footer>
```

Examples:
- `feat(auth): add password reset functionality`
- `fix(security): improve brute-force protection`
- `docs(readme): update installation instructions`
- `refactor(database): optimize query performance`

### Code Style
- Use consistent indentation (4 spaces)
- Follow Java naming conventions (camelCase for methods/variables, PascalCase for classes)
- Add JavaDoc comments for public methods
- Keep lines under 120 characters when possible
- No trailing whitespace

### Testing
- Write tests for new features
- Ensure all tests pass before submitting PR
- Include test files with your changes

## Submitting Changes

### Before Creating a Pull Request
1. Update your branch with latest changes:
   ```bash
   git fetch origin
   git rebase origin/develop
   ```

2. Ensure code quality:
   ```bash
   ./gradlew build
   ```

3. Test your changes:
   - Test in client mode
   - Test in server mode
   - Test both online and offline scenarios

### Creating a Pull Request
1. Push to your fork:
   ```bash
   git push origin feature/your-feature-name
   ```

2. Open a PR with:
   - Clear title describing the change
   - Detailed description of what changed and why
   - Reference any related issues using `#issue_number`
   - Screenshots for UI changes

3. PR Template (please follow):
   ```markdown
   ## Description
   Brief description of changes
   
   ## Type of Change
   - [ ] Bug fix
   - [ ] New feature
   - [ ] Breaking change
   - [ ] Documentation update
   
   ## Related Issues
   Fixes #(issue number)
   
   ## Testing
   Describe testing performed
   
   ## Checklist
   - [ ] Code follows style guidelines
   - [ ] Self-review completed
   - [ ] Comments added for complex logic
   - [ ] Tests added/updated
   - [ ] No new warnings generated
   ```

## Reporting Issues

### Bug Reports
Include:
- Minecraft version
- NeoForge version
- Exact steps to reproduce
- Expected behavior
- Actual behavior
- Logs/error messages
- Screenshots if applicable

### Feature Requests
Include:
- Clear description of the feature
- Why it would be useful
- Possible implementation approach
- Any relevant examples

## Security Issues
⚠️ **Do NOT create public issues for security vulnerabilities**

Please email security concerns to the project maintainers privately.

## Documentation

### README Updates
- Keep it clear and concise
- Include usage examples
- Update with new features
- Fix outdated information

### JavaDoc Comments
Add JavaDoc for:
- All public classes
- All public methods
- Complex private methods
- Non-obvious logic

Example:
```java
/**
 * Validates the player's password against the server records.
 *
 * @param player the player to validate
 * @param password the password to check
 * @return true if password is correct, false otherwise
 * @throws DatabaseException if database access fails
 */
public boolean validatePassword(Player player, String password) throws DatabaseException {
    // implementation
}
```

## Project Structure
```
src/main/java/io/SousaLJ/playersafelogin/
├── common/              # Shared code (client & server)
├── client/              # Client-side only code
├── server/              # Server-side only code
└── config/              # Configuration handling
```

## Versioning
This project follows [Semantic Versioning](https://semver.org/):
- MAJOR: Incompatible API changes
- MINOR: New functionality (backward compatible)
- PATCH: Bug fixes (backward compatible)

## Questions?
- Open a discussion on GitHub
- Check existing issues/PRs first
- Read the code comments and documentation

## License
By contributing, you agree that your contributions will be licensed under the project's license.

Happy coding! 🎮
