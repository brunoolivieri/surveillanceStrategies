# -*- coding: utf-8 -*-
"""
Created on Tue Aug 21 14:16:10 2018

@author: Bruno Olivieri
"""

#to use with Jupyter. Remove when using bash
#%matplotlib inline


import myloader
import mysettings
import myparser
import myploter




print('[Main] Starting ...')
print('[Main] Calling parser...')
myparser.run_parser() # which makes mysettings.FILEPARSED ready to import
print('[Main] Calling loader...')

myloader.run_old_ploter()


myploter.run_thesis_ploter()