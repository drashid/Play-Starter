#!/bin/bash

# Make directory to work in
TMPFILE=`mktemp -d /tmp/build-XXXXXXXXX`
if [ $? -ne 0 ]; then
  echo "$0: Can't create temp file, exiting..."
  exit 1
fi

# Find remote URL
REMOTE_URL=`git config --get remote.origin.url`
HEROKU_URL=`git config --get remote.heroku.url`
echo "Git remote url: $REMOTE_URL"
echo "Heroku remote url: $HEROKU_URL"

git clone $REMOTE_URL $TMPFILE
cd $TMPFILE
echo "---- Optimizing/Minifying Javascript Files ----"
play optimizejs
git add -A
echo "---- Committing Any Changes ----"
git commit -m "Deploy generated files."
echo "---- Tagging Build in Git ----"
TAG=`git describe`
git tag $TAG
REV=`git rev-parse $TAG`
echo "---- Pushing Changes to origin/master ----"
git push --tags origin master || exit 1
echo "---- Pushing REV ($REV) to heroku/master ----"
git push $HEROKU_URL $REV:master || exit 1

cd ..
echo "Removing Temporary Directory"
rm -rf $TMPFILE