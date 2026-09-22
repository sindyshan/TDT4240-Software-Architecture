
# Git Commit Prefixes

*feat:* A new feature (e.g., feat: add player stats tracking)
*fix:* A bug fix (e.g., fix: resolve multiplayer connection issue)
*chore:* Maintenance or non-functional changes (e.g., chore: update dependencies)
*refactor:* Code restructuring without changing functionality (e.g., refactor: optimize algorithm for turn validation)
*docs:* Documentation changes (e.g., docs: update API usage in README)
*test:* Adding or modifying tests (e.g., test: add unit test for score calculation)
*style:* Code style changes (e.g., style: format PlayerDAO file)
*perf:* Performance improvements (e.g., perf: optimize database query for leaderboard)
*build:* Build system or dependency changes (e.g., build: upgrade Gradle version)
*revert:* Reverting a previous commit (e.g., revert: undo recent database migration)

# Git Practices 

- Branch out all issues from dev branch 
- Write a commit message with prefixes and issue number. Ex. git commit -m "feat(#1): Create...."
- Commit message is written in imperative Ex. "Create... ", "Fix...", "Refactor..."
- In the pull request, write in description which issue this pull request closes. Ex "Closes issue #16"
- Make pull request to dev branch 
- Merge dev into main before delivering the project
