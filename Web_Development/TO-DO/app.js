function addItem() {
    const newItemText = document.getElementById('new-item').value.trim();

    if (newItemText === '') {
        alert('Please enter an item');
        return;
    }

    const itemList = document.getElementById('item-list');
    const li = document.createElement('li');

    const numberSpan = document.createElement('span');
    numberSpan.className = 'item-number';
    numberSpan.textContent = '';

    const textSpan = document.createElement('span');
    textSpan.className = 'item-text';
    textSpan.textContent = newItemText;

    const deleteButton = document.createElement('button');
    deleteButton.className = 'delete-button';
    deleteButton.textContent = 'Delete';
    deleteButton.addEventListener('click', function () {
        deleteItem(li);
    });

    li.append(numberSpan, textSpan, deleteButton);
    itemList.appendChild(li);

    document.getElementById('new-item').value = '';
    updateList();
}

function deleteItem(listItem) {
    const itemList = document.getElementById('item-list');
    itemList.removeChild(listItem);
    updateList();
}

function updateList() {
    const itemList = document.getElementById('item-list');
    const items = itemList.querySelectorAll('li');

    items.forEach((item, index) => {
        const numberSpan = item.querySelector('.item-number');
        if (numberSpan) {
            numberSpan.textContent = `${index + 1}. `;
        }
    });

    const itemCount = document.getElementById('item-count');
    itemCount.textContent = `Total items: ${items.length}`;
}

document.getElementById('new-item').addEventListener('keyup', function (event) {
    if (event.key === 'Enter') {
        addItem();
    }
});

updateList();