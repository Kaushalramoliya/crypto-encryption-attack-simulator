// ui/js/app.js

const { spawn } = require('child_process');
const fs = require('fs');
const path = require('path');

// Base path to your backend folder
const BACKEND = path.join(__dirname, '..', 'backend');
const CLASS_DIR = path.join(BACKEND, 'out', 'production', 'IS_Project');

// write textarea content into any backend file
function saveInputTo(fileName) {
  const text = document.getElementById('plaintext').value;
  fs.writeFileSync(path.join(BACKEND, fileName), text, 'utf8');
}

// run Java Main in interactive mode
function runJavaWith(choice, after) {
  // always save the latest plaintext.txt
  saveInputTo('plaintext.txt');

  // spawn the Java process
  const java = spawn('java', ['-cp', CLASS_DIR, 'Main'], {
    cwd: BACKEND
  });

  const algo = document.getElementById('algo').value.trim();
  let output = '';

  // feed algorithm, choice, then 0 (to exit loop)
  java.stdin.write(algo + '\n');
  java.stdin.write(choice + '\n');
  java.stdin.write('0\n');
  java.stdin.end();

  // collect stdout & stderr
  java.stdout.on('data', data => { output += data.toString(); });
  java.stderr.on('data', data => { output += data.toString(); });

  java.on('close', code => {
    document.getElementById('output').textContent = output;
    if (after) after();
  });
}

// load any backend text file into the tab pane
function loadFile(name) {
  const full = path.join(BACKEND, name);
  fs.readFile(full, 'utf8', (err, data) => {
    document.getElementById('tab-content').textContent =
      err ? `Error loading ${name}` : data;
  });
}

// button wiring
document.getElementById('run-all').onclick   = () => runJavaWith('1',   () => loadFile('encrypt.txt'));
document.getElementById('encrypt').onclick   = () => runJavaWith('2',   () => loadFile('encrypt.txt'));
document.getElementById('decrypt').onclick   = () => runJavaWith('3',   () => loadFile('decrypt.txt'));
document.getElementById('attack').onclick    = () => runJavaWith('4',   () => loadFile('encrypt_Attack.txt'));
document.getElementById('avalanche').onclick = () => runJavaWith('5',   () => loadFile('ce.txt'));

// saving textarea into arbitrary file
document.getElementById('save-input').onclick = () => {
  const target = document.getElementById('save-target').value;
  saveInputTo(target);
  alert(`Saved to ${target}`);
};

// tab clicks
document.querySelectorAll('.tab').forEach(btn => {
  btn.onclick = () => {
    document.querySelectorAll('.tab').forEach(t => t.classList.remove('active'));
    btn.classList.add('active');
    loadFile(btn.dataset.target);
  };
});
