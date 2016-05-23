/// <reference path="../../../typings/index.d.ts" />

import { EventEmitter } from 'events';
import { install, Emitter } from 'typings-core';

const cwd     = process.argv[2];
const prod    = process.argv[3] == 'true';
const emitter = new EventEmitter();

install({ cwd: cwd, production: prod, emitter: emitter }).
  then(
    s   => { return {"success": true} },
    err => { throw new Error(err)} );
