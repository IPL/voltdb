# This file is part of VoltDB.
# Copyright (C) 2008-2012 VoltDB Inc.
#
# Permission is hereby granted, free of charge, to any person obtaining
# a copy of this software and associated documentation files (the
# "Software"), to deal in the Software without restriction, including
# without limitation the rights to use, copy, modify, merge, publish,
# distribute, sublicense, and/or sell copies of the Software, and to
# permit persons to whom the Software is furnished to do so, subject to
# the following conditions:
#
# The above copyright notice and this permission notice shall be
# included in all copies or substantial portions of the Software.
#
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
# EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
# MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
# IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
# OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
# ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
# OTHER DEALINGS IN THE SOFTWARE.

# All the commands supported by the Voter application.

import os

class VoterServer(VOLT.ServerBundle):
    def __init__(self, action):
        VOLT.ServerBundle.__init__(self, action)
    def initialize(self, verb):
        verb.add_command_arguments('voter.jar')
        verb.add_to_classpath('obj')
        VOLT.ServerBundle.initialize(self, verb)

class VoterClient(VOLT.JavaBundle):
    def __init__(self, class_name):
        VOLT.JavaBundle.__init__(self, 'voter.%s' % class_name)
    def initialize(self, verb):
        verb.add_to_classpath('obj')
        VOLT.JavaBundle.initialize(self, verb)

@VOLT.Command(
    description = 'Build the Voter application and catalog.',
    options = VOLT.BooleanOption('-C', '--conditional', 'conditional',
                                 'only build when the catalog file is missing'),
)
def build(runner):
    if not runner.opts.conditional or not os.path.exists('voter.jar'):
        runner.java.compile('obj', 'src/voter/*.java', 'src/voter/procedures/*.java')
        runner.call('volt.compile', '-c', 'obj', '-o', 'voter.jar', 'ddl.sql')

@VOLT.Command(
    description = 'Clean the Voter build output.',
)
def clean(runner):
    runner.shell('rm', '-rfv', 'obj', 'debugoutput', 'voter.jar', 'voltdbroot')

@VOLT.Command(
     description = 'Start the Voter VoltDB server.',
     bundles = VoterServer('start'),
)
def server(runner):
    runner.call('build', '-C')
    runner.go()

@VOLT.Command(
    description = 'Run the Voter asynchronous benchmark.',
    bundles = VoterClient('AsyncBenchmark'),
)
def async(runner):
    runner.call('build', '-C')
    runner.go()

@VOLT.Command(
    description = 'Run the Voter synchronous benchmark.',
    bundles = VoterClient('SyncBenchmark'),
)
def sync(runner):
    runner.call('build', '-C')
    runner.go()

@VOLT.Command(
    description = 'Run the Voter JDBC benchmark.',
    bundles = VoterClient('JDBCBenchmark'),
)
def jdbc(runner):
    runner.call('build', '-C')
    runner.go()

@VOLT.Command(
    description = 'Run the Voter simple benchmark.',
    bundles = VoterClient('SimpleBenchmark'),
)
def simple(runner):
    runner.call('build', '-C')
    runner.go()