document.addEventListener('DOMContentLoaded', function () {
    const currentMonthElement = document.getElementById('currentMonth');
    const yearMonthElement = document.getElementById('yearMonth');
    const calendarDaysElement = document.getElementById('calendarDays');

    function renderCalendar(year, month) {
        const currentDate = new Date(year, month - 1);
        const yearMonth = `${currentDate.getFullYear()}-${String(currentDate.getMonth() + 1).padStart(2, '0')}`;
        const monthName = currentDate.toLocaleString('default', { month: 'long' });

        currentMonthElement.textContent = `${monthName} ${currentDate.getFullYear()}`;
        yearMonthElement.value = yearMonth;

        const daysInMonth = new Date(year, month, 0).getDate();
        calendarDaysElement.innerHTML = '';

        for (let day = 1; day <= daysInMonth; day++) {
            const dayElement = document.createElement('div');
            const dayDate = new Date(year, month - 1, day);
            const isPast = dayDate < new Date();

            dayElement.className = `calendar-day ${isPast ? 'disabled' : ''}`;
            dayElement.dataset.date = dayDate.toISOString().split('T')[0];
            dayElement.innerHTML = `<span>${String(day).padStart(2, '0')}</span>`;

            dayElement.addEventListener('click', function () {
                if (!isPast) {
                    window.location.href = `/calendar/day/${this.dataset.date}`;
                }
            });

            calendarDaysElement.appendChild(dayElement);
        }
    }

    function navigateMonth(offset) {
        const [year, month] = yearMonthElement.value.split('-').map(Number);
        const newDate = new Date(year, month - 1);
        newDate.setMonth(newDate.getMonth() + offset);

        renderCalendar(newDate.getFullYear(), newDate.getMonth() + 1);
    }

    const today = new Date();
    renderCalendar(today.getFullYear(), today.getMonth() + 1);
});
