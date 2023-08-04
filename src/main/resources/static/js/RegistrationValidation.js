
// Получаем элементы полей пароля и подтверждения пароля
const passwordInput = document.getElementById('clientPassword');
const confirmPasswordInput = document.getElementById('confirmPassword');
const passwordMatchError = document.querySelector('.password-match-error');

// Функция для проверки совпадения паролей
function checkPasswordMatch() {
    if (passwordInput.value !== confirmPasswordInput.value) {
        passwordMatchError.style.display = 'block';
        return false;
    } else {
        passwordMatchError.style.display = 'none';
        return true;
    }
}

// Обработчик события при отправке формы
document.querySelector('form').addEventListener('submit', function (event) {
    if (!checkPasswordMatch()) {
        event.preventDefault(); // Предотвращаем отправку формы при несовпадении паролей
    }
});