# 🔐 Crypto Encryption & Attack Demo

A fully interactive cryptographic simulation using **DES/AES**, complete with encryption, decryption, attack corruption, and avalanche effect — controlled via a stunning animated browser UI powered by Java + HTML + CSS + JavaScript.

---

## 📘 Description

This project is designed to demonstrate the **core principles of Information Security** using industry-standard encryption techniques and simulate both safe and unsafe data handling.

🔑 The program includes:
- **Encryption & Decryption** using **DES** and **AES**
- **File-level attack simulation** by altering ciphertext
- **Avalanche Effect Demonstration** — flip 1 bit, change many
- **Dynamic terminal-style logs**
- **Live file preview system** for `.txt` results
- All features wrapped in a clean, animated, and user-friendly UI

---

## 🎯 Key Features

### 🔣 Algorithm Selector
- Choose between `DES` or `AES` encryption algorithms

### 🧪 Core Actions (Buttons)
- `Run All`: Execute **encryption → decryption → attack → avalanche** in one go
- `Encrypt`: Encrypt input text and store into `encrypt.txt`
- `Decrypt`: Read and decrypt from `encrypt.txt`, store in `decrypt.txt`
- `Attack`: Randomly corrupt the encrypted data, simulate failed decryption
- `Avalanche`: Demonstrate how a single bit change causes widespread ciphertext change

### 📂 File Save & Preview
- **File Selector**: Choose where to save your input (`plaintext.txt`, etc.)
- **Live Preview Tabs**: See file contents from:
  - `plaintext.txt`
  - `encrypt.txt`
  - `decrypt.txt`
  - `encrypt_Attack.txt`
  - `decrypt_Attack.txt`

### 📜 Log Output
- Terminal-like panel showing all steps and algorithm results

---

## 🧰 Tech Stack

| Layer | Tech |
|-------|------|
| 🧠 Backend | Java 11+ (javax.crypto, KeyGenerator, Cipher) |
| 🖼 Frontend | HTML5 + CSS3 + Vanilla JavaScript |
| 🎨 Styling | Fully custom dark theme + animated gradient backgrounds |
| 🧩 JS API | Node.js APIs: `fs`, `child_process` (for Java execution) |
| 📋 UX | Hover animations, scale transitions, terminal panels, file tabs |
| ⚙️ Other | Live panel layout using CSS Grid, Flexbox, responsive media queries |

---

## 📁 Project Structure

