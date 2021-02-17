#!/bin/bash

TESTS_TME7="\
pobj.pinboard.document.test.ClipRectTest \
pobj.pinboard.document.test.BoardTest \
pobj.pinboard.document.test.ClipEllipseTest \
pobj.pinboard.editor.tools.test.ToolRectTest \
pobj.pinboard.editor.tools.test.ToolEllipseTest \
"

TESTS_TME8="\
pobj.pinboard.editor.test.SelectionTest \
pobj.pinboard.editor.tools.test.ToolSelectionTest \
pobj.pinboard.editor.test.ClipboardTest \
pobj.pinboard.editor.test.ClipboardListenerTest \
"

TESTS_TME9="\
pobj.pinboard.document.test.ClipGroupTest \
pobj.pinboard.editor.commands.test.CommandAddTest \
pobj.pinboard.editor.commands.test.CommandMoveTest \
pobj.pinboard.editor.commands.test.CommandGroupTest \
pobj.pinboard.editor.commands.test.CommandUngroupTest \
pobj.pinboard.editor.test.CommandStackTest \
"

TESTS="$TESTS_TME7 $TESTS_TME8 $TESTS_TME9"


#########

CP=/home/pobj/pobj-ci.jar:/usr/share/java/junit4.jar:/usr/share/java/hamcrest-core-1.3.jar

SRC=./src

echo "Date :"
date
echo

echo "Répertoire courant :"
pwd
echo

echo "Liste des fichiers :"
ls -lR
echo

echo "Lancement des tests"
echo java -cp $CP:. pobj.ci.Runner -c -cp $CP -sp $SRC $TESTS
java -cp $CP:. pobj.ci.Runner -c -cp $CP -sp $SRC $TESTS
