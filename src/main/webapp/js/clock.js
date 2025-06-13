const clockContainer = document.getElementById("clock");
const canvas = clockContainer.getContext("2d");

canvas.translate(150, 150);

function drawClock() {
    const now = new Date();
    const secondsAngle = now.getSeconds() / 60 * (Math.PI * 2);
    const minutesAngle = now.getMinutes() / 60 * (Math.PI * 2) + secondsAngle / 60;
    const hoursAngle = (now.getHours() % 12) / 12 * (Math.PI * 2) + minutesAngle / 12;
    canvas.clearRect(-150, -150, 300, 300);
    drawCircle(0, 0, 145, 'white');
    drawNumbers();
    drawHand(secondsAngle, 100, 2, 'rgba(92, 92, 186, 1)');
    drawHand(minutesAngle, 80, 3, 'black');
    drawHand(hoursAngle, 60, 3, 'black');
    requestAnimationFrame(drawClock);
}

function drawCircle(x, y, radius, color) {
    canvas.beginPath();
    canvas.arc(x, y, radius, 0, Math.PI * 2);
    canvas.fillStyle = color;
    canvas.fill();
    canvas.strokeStyle = '#000';
    canvas.lineWidth = 2;
    canvas.stroke();
}

function drawNumbers() {
    const fontSize = 20;
    canvas.font = `bold ${fontSize}px Arial`;
    canvas.textAlign = 'center';
    canvas.textBaseline = 'middle';
    canvas.fillStyle = 'rgba(92, 92, 186, 1)';
    for (let i = 1; i <= 12; i++) {
        const angle = (i * Math.PI / 6) - Math.PI / 2;
        const x = 120 * Math.cos(angle);
        const y = 120 * Math.sin(angle);
        canvas.fillText(i, x, y);
    }
}

function drawHand(angle, length, width, color) {
    canvas.beginPath();
    canvas.moveTo(0, 0);
    const x = Math.sin(angle) * length;
    const y = -Math.cos(angle) * length;
    canvas.lineTo(x, y);
    canvas.lineWidth = width;
    canvas.lineCap = 'round';
    canvas.strokeStyle = color;
    canvas.stroke();
}

drawClock();