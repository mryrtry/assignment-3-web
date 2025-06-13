const tableValues = document.getElementById('hidden_output').value;
const table = document.getElementById('table');

function formatRequestTime(requestTime) {
    if (typeof requestTime !== 'string' || !requestTime.includes('T')) {
        throw new Error('Invalid ISO date string');
    }

    // Создаем объект Date из ISO строки
    const serverDate = new Date(requestTime);

    // Преобразуем дату в локальное время клиента
    const localDate = new Date(serverDate.toLocaleString('en-US', {timeZone: 'Europe/Moscow'}));

    // Форматируем дату в читаемый формат
    const options = {
        year: 'numeric',
        month: 'long',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit',
        second: '2-digit',
        timeZoneName: 'short'
    };

    return localDate.toLocaleString(navigator.language, options);
}

function formatNanosecondsToMilliseconds(nanoseconds) {
    if (typeof nanoseconds !== 'number' || isNaN(nanoseconds)) {
        throw new Error('Invalid input. Please provide a valid number of nanoseconds.');
    }

    // Преобразуем наносекунды в миллисекунды
    const milliseconds = nanoseconds / 1_000_000;

    // Форматируем вывод с добавлением " ms"
    return `${milliseconds.toFixed(3)} ms`; // Округляем до 3 знаков после запятой
}


function addFieldToRow(row, value) {
    let column = document.createElement('p');
    column.innerHTML = value;
    row.appendChild(column);
}

function addRowToTable(point) {
    let row = document.createElement('div');
    row.className = "table_row";
    if (point.inArea) row.className += " inarea";
    addFieldToRow(row, point.x);
    addFieldToRow(row, point.y);
    addFieldToRow(row, point.r);
    addFieldToRow(row, (point.inArea) ? "In area" : "Not in area");
    addFieldToRow(row, formatRequestTime(point.requestTime));
    addFieldToRow(row, formatNanosecondsToMilliseconds(point.processingTime));
    table.appendChild(row);
}

window.addEventListener("DOMContentLoaded", function () {
    const tableValuesJSON = JSON.parse(tableValues);
    console.log(tableValuesJSON)
    for (let i = 0; i < tableValuesJSON.length; i++) {
        addRowToTable(tableValuesJSON[i]);
    }
});
