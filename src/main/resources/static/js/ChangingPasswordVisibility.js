function togglePasswordVisibility() {
    const passwordInput = document.getElementById('clientPassword');
    const passwordConfirm = document.getElementById('confirmPassword');
    const showPasswordIcon = document.querySelector('.show-password-icon');
    //show--confirm-password-icon
    const showConfirmPasswordIcon = document.querySelector('.show-confirm-password-icon');

    if (passwordInput.type === 'password') {
        passwordInput.type = 'text';
        passwordConfirm.type = 'text';
        showConfirmPasswordIcon.querySelector('img:first-child').style.display = 'none';
        showConfirmPasswordIcon.querySelector('img:last-child').style.display = 'inline';
        showPasswordIcon.querySelector('img:first-child').style.display = 'none';
        showPasswordIcon.querySelector('img:last-child').style.display = 'inline';
    } else {
        passwordInput.type = 'password';
        passwordConfirm.type = 'password';
        showConfirmPasswordIcon.querySelector('img:first-child').style.display = 'inline';
        showConfirmPasswordIcon.querySelector('img:last-child').style.display = 'none';
        showPasswordIcon.querySelector('img:first-child').style.display = 'inline';
        showPasswordIcon.querySelector('img:last-child').style.display = 'none';
    }
}