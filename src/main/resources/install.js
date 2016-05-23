"use strict";
const events = require('events');
const typings = require('typings-core');

const cwd     = process.argv[2];
const prod    = process.argv[3] == 'true';
const emitter = new events.EventEmitter();

typings.install({ cwd: cwd, production: prod, emitter: emitter }).
    then(function (s) {
      return { "success": true };
    }, function (err) {
      console.log(err);
      throw new Error(err);
    });
