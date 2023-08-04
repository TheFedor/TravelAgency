function togglePasswordVisibility() {
    const passwordInput = document.getElementById('clientPassword');
    const showPasswordIcon = document.querySelector('.show-password-icon');

    if (passwordInput.type === 'password') {
        passwordInput.type = 'text';
        showPasswordIcon.querySelector('img:first-child').style.display = 'none';
        showPasswordIcon.querySelector('img:last-child').style.display = 'inline';
    } else {
        passwordInput.type = 'password';
        showPasswordIcon.querySelector('img:first-child').style.display = 'inline';
        showPasswordIcon.querySelector('img:last-child').style.display = 'none';
    }
}