const container = document.getElementById("canvas");
const canvas = container.getContext("2d");
const block_size = 50;

function clear_canvas() {
    canvas.clearRect(-300, -300, 600, 600);
}

function draw_line(start_coord_x, start_coord_y, finish_coord_x, finish_coord_y) {
    canvas.beginPath();
    canvas.moveTo(start_coord_x, start_coord_y);
    canvas.lineTo(finish_coord_x, finish_coord_y);
    canvas.closePath();
    canvas.stroke();
}

function draw_point(x, y, inarea) {
    if (inarea == null) {
        canvas.fillStyle = "rgba(95, 95, 95, 1)";
        canvas.strokeStyle = "rgba(95, 95, 95, 0.3)";
    } else if (inarea) {
        canvas.fillStyle = "rgba(92, 92, 186, 1)";
        canvas.strokeStyle = "rgba(92, 92, 186, 0.3)";
    } else {
        canvas.fillStyle = "rgba(186, 92, 109, 1)";
        canvas.strokeStyle = "rgba(186, 92, 109, 0.3)";
    }

    canvas.setLineDash([12.5, 12.5]);
    canvas.beginPath();
    canvas.moveTo(x * block_size, -y * block_size);
    canvas.lineTo(x * block_size, 0);
    canvas.moveTo(x * block_size, -y * block_size);
    canvas.lineTo(0, -y * block_size);
    canvas.stroke();

    canvas.beginPath();
    canvas.arc(x * block_size, -y * block_size, 4, 0, 2 * Math.PI);
    canvas.closePath();
    canvas.fill();

    canvas.strokeStyle = "black";
    canvas.fillStyle = "black";
}

function draw_coords() {
    draw_line(0, -300, 0, 300);
    draw_line(-300, 0, 300, 0);

    canvas.beginPath();
    canvas.moveTo(-5, -295);
    canvas.lineTo(0, -300);
    canvas.lineTo(5, -295);
    canvas.closePath();
    canvas.fill();
    canvas.fillText("y", 14, -295);

    canvas.beginPath();
    canvas.moveTo(295, -5);
    canvas.lineTo(300, 0);
    canvas.lineTo(295, 5);
    canvas.closePath();
    canvas.fill();
    canvas.fillText("x", 295, 14);

    for (let x = -275; x <= 275; x += 25) {
        if (x !== 0) {
            let rounded = (x / block_size).toFixed(1);
            canvas.fillText(rounded, x - 7, 14);
            canvas.fillText(rounded, 7, -x + 2.5);
            draw_line(-5, x, 5, x);
            draw_line(x, -5, x, 5);
        }
    }
}

function draw_graph(radius) {
    canvas.fillStyle = "rgba(92, 92, 186, 0.4)";
    canvas.fillRect(-radius * block_size, -radius * block_size, radius * block_size, radius * block_size);

    canvas.beginPath();
    canvas.moveTo(0, 0);
    canvas.lineTo(0, -radius * block_size);
    canvas.lineTo(radius * block_size, 0);
    canvas.closePath();
    canvas.fill();

    canvas.beginPath();
    canvas.moveTo(0, 0);
    canvas.arc(0, 0, radius * block_size / 2, 0, Math.PI / 2);
    canvas.closePath();
    canvas.fill();

    canvas.fillStyle = "black";
}

function redraw_canvas(radius, values) {
    clear_canvas();

    if (!values || values.length === 0) {
        draw_graph(radius);
        draw_coords();
        return;
    }

    draw_graph(radius);

    for (let i = 0; i < values.length - 1; i++) {
        let point = values[i];
        draw_point(parseFloat(point.x), parseFloat(point.y), null)
    }

    const last_point = values[values.length - 1]
    draw_point(parseFloat(last_point.x), parseFloat(last_point.y), last_point.inArea);

    draw_coords();
}


window.addEventListener("DOMContentLoaded", function () {
    const selectElement = document.getElementsByClassName('radius_select')[0];
    const resultValues = document.getElementById('hidden_output').value;
    const resultList = JSON.parse(resultValues);
    let radius = 1;
    if (!resultList || resultList.length !== 0) radius = resultList[resultList.length - 1].r;
    for (let i = 0; i < selectElement.options.length; i++) {
        if (selectElement.options[i].value == radius) {
            selectElement.options[i].selected = true;
            break;
        }
    }
    redraw_canvas(radius, resultList);
});

document.getElementsByClassName('radius_select')[0].addEventListener("change", function () {
    const resultValues = document.getElementById('hidden_output').value;
    const resultList = JSON.parse(resultValues);
    const selectedRadius = parseFloat(this.value);
    redraw_canvas(selectedRadius, resultList)
});

container.addEventListener("click", function (event) {
    const clientRect = canvas.canvas.getBoundingClientRect();
    const x_client = ((event.clientX - clientRect.left) / block_size - 6).toFixed(2);
    const y_client = -(((event.clientY - clientRect.top) / block_size) - 6).toFixed(2);

    document.getElementsByClassName("x_selector")[0].value = x_client;
    document.getElementsByClassName("y_selector")[0].value = y_client;

    setTimeout(() => {
        document.getElementsByClassName('submit_form_button')[0].click();
    }, 500);

    draw_point(x_client, y_client, null);
});

canvas.translate(300, 300);