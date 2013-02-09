#!/bin/sh
# AUTO-GENERATED FILE, DO NOT EDIT!
if [ -f $1.org ]; then
  sed -e 's!^K:/cygwin/lib!/usr/lib!ig;s! K:/cygwin/lib! /usr/lib!ig;s!^K:/cygwin/bin!/usr/bin!ig;s! K:/cygwin/bin! /usr/bin!ig;s!^K:/cygwin/!/!ig;s! K:/cygwin/! /!ig;s!^K:!/cygdrive/k!ig;s! K:! /cygdrive/k!ig;s!^C:!/cygdrive/c!ig;s! C:! /cygdrive/c!ig;' $1.org > $1 && rm -f $1.org
fi
