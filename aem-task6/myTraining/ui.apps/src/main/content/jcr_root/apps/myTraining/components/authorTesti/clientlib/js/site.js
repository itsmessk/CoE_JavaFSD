// Function to fetch data from the API
async function fetchData() {
    try {
        const response = await fetch('https://sampleapi-production-13b0.up.railway.app/authors'); // Update API endpoint
        const data = await response.json();
        return data.authors; // Access 'authors' array
    } catch (error) {
        console.error('Error fetching data:', error);
        return [];
    }
}

// Function to render data in cards
async function renderData() {
    const container = document.querySelector('.author-container');
    const loadMoreButton = document.getElementById('load-more');

    // Fetch the number of cards to show from the data attribute
    const cardsToShow = parseInt(loadMoreButton.dataset.cardsToShow, 10);
    
    let currentIndex = 0;

    // Fetch data from the API
    const authors = await fetchData();

    if (!authors.length) {
        container.innerHTML = '<p>No data available.</p>';
        return;
    }

    // Function to show a chunk of cards
    function showCards(startIndex, endIndex) {
        for (let i = startIndex; i < endIndex && i < authors.length; i++) {
            const author = authors[i];
            const card = document.createElement('div');
            card.classList.add('card');

            const name = document.createElement('h2');
            name.textContent = author.name;

            const bio = document.createElement('p');
            bio.textContent = author.bio;

            card.appendChild(name);
            card.appendChild(bio);
            container.appendChild(card);
        }
    }

    // Initially show the first 'cardsToShow' authors
    showCards(currentIndex, currentIndex + cardsToShow);
    currentIndex += cardsToShow;

    // Load more cards when the button is clicked
    loadMoreButton.addEventListener('click', function () {
        showCards(currentIndex, currentIndex + cardsToShow);
        currentIndex += cardsToShow;

        // Hide the "Load More" button if all authors are loaded
        if (currentIndex >= authors.length) {
            loadMoreButton.style.display = 'none';
        }
    });

    // Hide the "Load More" button if all authors are already shown
    if (currentIndex >= authors.length) {
        loadMoreButton.style.display = 'none';
    }
}

// Call the renderData function to display data
renderData();
