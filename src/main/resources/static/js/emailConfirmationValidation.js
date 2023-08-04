
// Получаем элементы полей пароля и подтверждения пароля
const codeMatchError = document.querySelector('.six-digit-code-match-error');
const sixDigitCode = document.getElementById('sixDigitCodeContainer').getAttribute('data-six-digit-code');

//функция для проверки совпадают ли код пользователя и реальный код
function checkCodeMatch() {
    const sixDigitCodeClient = document.getElementById('emailAddressConfirm').value;
    if (sixDigitCodeClient !== sixDigitCode) {
        codeMatchError.style.display = 'block';
        return false;
    } else {
        codeMatchError.style.display = 'none';
        return true;
    }
}

// Обработчик события при отправке формы
document.querySelector('form').addEventListener('submit', function (event) {
    if (!checkCodeMatch()) {
        event.preventDefault(); // Предотвращает отправку формы при несовпадении паролей
    }
})