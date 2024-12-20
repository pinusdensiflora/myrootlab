const yearList = document.getElementById("yearList");
const selectedYears = document.getElementById("selectedYears");
const selectedYearSet = new Set(); // To keep track of selected years
const warning = document.getElementById("warning");
const MAX_SELECTION = 5;

// Populate the list of available years
for (let year = 1970; year <= 2099; year++) {
	const button = document.createElement("button");
	button.textContent = year;
	button.dataset.year = year; // Add a data attribute to track the year
	button.addEventListener("click", () => toggleYear(year));
	yearList.appendChild(button);
}

// Function to toggle the selection of a year
function toggleYear(year) {
	if (selectedYearSet.has(year)) {
		// If the year is already selected, remove it
		selectedYearSet.delete(year);
		warning.textContent = ''; // Clear warning message
	} else {
		if (selectedYearSet.size >= MAX_SELECTION) {
			// Show warning if maximum selection is reached
			warning.textContent = `년도 선택은 최대 ${MAX_SELECTION}개 까지 가능합니다.`;
			return;
		}
		// If the year is not selected, add it
		selectedYearSet.add(year);
	}
	updateSelectedYears();
}

// Function to update the selected years display
function updateSelectedYears() {
	// Clear the current list
	selectedYears.innerHTML = '';

	// Get sorted years
	const sortedYears = Array.from(selectedYearSet).sort((a, b) => a - b);

	// Add sorted years to the display
	sortedYears.forEach(year => {
		const button = document.createElement("button");
		button.textContent = year;
		button.dataset.year = year;
		button.addEventListener("click", () => toggleYear(year));
		selectedYears.appendChild(button);
	});
}