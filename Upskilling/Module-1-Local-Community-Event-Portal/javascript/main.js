/* =============================================================
   main.js  –  JavaScript Module 1 Exercises
   Project: Local Community Event Portal
   ============================================================= */

// ---------------------------------------------------------------
// Ex 1: Basic Setup – page load alert + console log
// ---------------------------------------------------------------
window.addEventListener('load', () => {
  console.log('Welcome to the Community Portal');   // Ex 1
  // alert('Welcome to the Community Event Portal!'); // uncomment if needed
  loadPage();
});

// ---------------------------------------------------------------
// Ex 2: Syntax, Data Types, Operators
// ---------------------------------------------------------------
const eventName = 'Art Exhibition';          // const for fixed value
const eventDate = '15 June 2025';
let availableSeats = 50;                     // let for mutable value

// Template literal
const eventInfo = `Event: ${eventName} | Date: ${eventDate} | Seats: ${availableSeats}`;
console.log(eventInfo);

// Seat management with ++/--
function decrementSeat() { availableSeats--; console.log('Seats left:', availableSeats); }
function incrementSeat() { availableSeats++; console.log('Seats left:', availableSeats); }


// ---------------------------------------------------------------
// Ex 3: Conditionals, Loops, Error Handling
// ---------------------------------------------------------------
const rawEvents = [
  { id: 1,  name: 'Art Exhibition',  category: 'art',       date: '2025-06-15', seats: 30,  fee: 0,   past: false },
  { id: 2,  name: 'Music Festival',  category: 'music',     date: '2025-06-22', seats: 0,   fee: 50,  past: false },
  { id: 3,  name: 'Baking Workshop', category: 'workshop',  date: '2025-06-29', seats: 12,  fee: 150, past: false },
  { id: 4,  name: 'Yoga Camp',       category: 'wellness',  date: '2025-07-06', seats: 20,  fee: 100, past: false },
  { id: 5,  name: 'Kids Fair',       category: 'community', date: '2025-07-13', seats: 100, fee: 0,   past: false },
  { id: 6,  name: 'Clean Drive',     category: 'community', date: '2025-07-20', seats: 50,  fee: 0,   past: false },
  { id: 7,  name: 'Retro Night',     category: 'music',     date: '2024-12-20', seats: 0,   fee: 200, past: true  },
];

// Ex 3: Filter only upcoming events (not past, not full)
function getValidEvents(events) {
  return events.filter(e => !e.past && e.seats > 0);
}

// ---------------------------------------------------------------
// Ex 4: Functions, Closures, Higher-Order Functions
// ---------------------------------------------------------------

// Closure: track total registrations per category
function makeCategoryCounter() {
  const counts = {};
  return function(category) {
    counts[category] = (counts[category] || 0) + 1;
    console.log(`Registrations for ${category}:`, counts[category]);
    return counts[category];
  };
}
const trackRegistration = makeCategoryCounter();

// Higher-order: filter by category callback
function filterEventsByCategory(events, predicate) {
  return events.filter(predicate);
}

// Add new event
function addEvent(list, eventObj) {
  list.push(eventObj);
  return list;
}

// Register user with try-catch (Ex 3)
function registerUser(eventId) {
  try {
    const event = rawEvents.find(e => e.id === eventId);
    if (!event) throw new Error('Event not found.');
    if (event.past) throw new Error('This event has already passed.');
    if (event.seats <= 0) throw new Error('No seats available.');
    event.seats--;
    trackRegistration(event.category);
    return { success: true, message: `Registered for "${event.name}". Seats left: ${event.seats}` };
  } catch (err) {
    console.error('Registration error:', err.message);
    return { success: false, message: err.message };
  }
}

// ---------------------------------------------------------------
// Ex 5: Objects and Prototypes
// ---------------------------------------------------------------
function Event(id, name, category, date, seats, fee) {
  this.id = id; this.name = name; this.category = category;
  this.date = date; this.seats = seats; this.fee = fee;
}

Event.prototype.checkAvailability = function () {
  return this.seats > 0 ? `${this.seats} seats available` : 'Sold out';
};

const sampleEvent = new Event(10, 'Tech Talk', 'workshop', '2025-08-01', 25, 200);
console.log('Object entries:', Object.entries(sampleEvent));
console.log('Availability:', sampleEvent.checkAvailability());

// ---------------------------------------------------------------
// Ex 6: Arrays & Methods
// ---------------------------------------------------------------
let events = [...rawEvents];          // spread to clone (Ex 10)

// push – add a new event
events.push({ id: 8, name: 'Jazz Evening', category: 'music', date: '2025-08-10', seats: 40, fee: 80, past: false });

// filter – music events only
const musicEvents = events.filter(e => e.category === 'music');
console.log('Music events:', musicEvents.map(e => e.name));

// map – format display string
const formattedCards = events.map(e => `${e.category === 'workshop' ? 'Workshop on' : ''} ${e.name}`.trim());
console.log('Formatted:', formattedCards);


// ---------------------------------------------------------------
// Ex 7 & 3: DOM Manipulation – render event cards
// ---------------------------------------------------------------
function renderEvents(list) {
  const container = document.querySelector('#eventList');
  if (!container) return;
  container.innerHTML = '';

  if (list.length === 0) {
    container.innerHTML = '<p style="color:#888;padding:10px;">No events found.</p>';
    updateTotal(0);
    return;
  }

  list.forEach(e => {
    // Ex 7: createElement
    const card = document.createElement('div');
    card.className = 'event-card';
    card.dataset.id = e.id;

    const badge = e.fee === 0
      ? `<span class="badge badge-free">Free</span>`
      : `<span class="badge badge-paid">₹${e.fee}</span>`;

    const seatsText = e.seats > 0 ? `${e.seats} seats left` : 'Sold Out';
    const btnDisabled = e.seats <= 0 ? 'disabled' : '';

    card.innerHTML = `
      <h3>${e.name}</h3>
      <div class="meta">📅 ${e.date} &nbsp;|&nbsp; 🏷 ${e.category} &nbsp; ${badge}</div>
      <div class="seats">🪑 ${seatsText}</div>
      <button class="btn-reg" ${btnDisabled} onclick="handleRegister(${e.id})">
        ${e.seats > 0 ? 'Register' : 'Full'}
      </button>
    `;
    container.appendChild(card);
  });

  updateTotal(list.length);
}

function updateTotal(count) {
  const el = document.querySelector('#totalCount');
  if (el) el.textContent = `Showing ${count} event(s).`;
}

// ---------------------------------------------------------------
// Ex 8: Event Handling – Register button, category filter, keydown search
// ---------------------------------------------------------------
function handleRegister(id) {
  const result = registerUser(id);
  alert(result.message);
  if (result.success) renderEvents(getFilteredList());
}

// Category filter (onchange)
document.addEventListener('DOMContentLoaded', () => {
  const catFilter = document.getElementById('categoryFilter');
  if (catFilter) {
    catFilter.addEventListener('change', () => renderEvents(getFilteredList()));
  }

  // Keydown quick search (Ex 8)
  const searchEl = document.getElementById('searchInput');
  if (searchEl) {
    searchEl.addEventListener('keydown', () => {
      // use setTimeout 0 to get updated value after keydown
      setTimeout(() => renderEvents(getFilteredList()), 0);
    });
  }
});

function getFilteredList() {
  const cat  = (document.getElementById('categoryFilter')?.value || '').toLowerCase();
  const term = (document.getElementById('searchInput')?.value   || '').toLowerCase();
  return events.filter(e => {
    const matchCat  = !cat  || e.category === cat;
    const matchTerm = !term || e.name.toLowerCase().includes(term);
    return matchCat && matchTerm && !e.past;
  });
}

function resetFilter() {
  document.getElementById('categoryFilter').value = '';
  document.getElementById('searchInput').value = '';
  renderEvents(getFilteredList());
}


// ---------------------------------------------------------------
// Ex 9 & 12: Async/Await + Fetch API (mock JSON endpoint)
// ---------------------------------------------------------------
async function fetchEvents() {
  const spinner = document.getElementById('spinner');
  const result  = document.getElementById('apiResult');
  spinner.style.display = 'block';
  result.textContent = '';

  try {
    // Using JSONPlaceholder as a mock REST API
    const response = await fetch('https://jsonplaceholder.typicode.com/posts?_limit=5');
    if (!response.ok) throw new Error(`HTTP error: ${response.status}`);
    const data = await response.json();
    result.textContent = JSON.stringify(data, null, 2);
  } catch (err) {
    result.textContent = 'Error: ' + err.message;
  } finally {
    spinner.style.display = 'none';
  }
}

// Ex 12: POST with fetch (simulated)
async function postRegistration(formData) {
  return new Promise((resolve) => {
    setTimeout(() => {        // simulated delay (Ex 12)
      resolve({ ok: true, message: 'Registration saved to server!' });
    }, 1200);
  });
}


// ---------------------------------------------------------------
// Ex 10: Modern JS – destructuring, spread, default params
// ---------------------------------------------------------------
function formatEvent({ name, date = 'TBD', fee = 0 } = {}) {
  return `${name} | ${date} | ${fee === 0 ? 'Free' : '₹' + fee}`;
}
console.log('Modern JS destructuring:', formatEvent(rawEvents[0]));

// Spread to clone before filtering (avoids mutating original)
const clonedForFilter = [...rawEvents];
console.log('Cloned events:', clonedForFilter.length);


// ---------------------------------------------------------------
// Ex 11: Working with Forms – validation + prevent default
// ---------------------------------------------------------------
document.addEventListener('DOMContentLoaded', () => {
  const form = document.getElementById('regForm');
  if (!form) return;

  form.addEventListener('submit', async (e) => {
    e.preventDefault();    // Ex 11: prevent default

    const name  = form.elements['rName'].value.trim();
    const email = form.elements['rEmail'].value.trim();
    const event = form.elements['rEvent'].value;

    let valid = true;

    // Inline validation
    if (!name) {
      showErr('nameErr', 'Name is required.'); valid = false;
    } else clearErr('nameErr');

    if (!email || !email.includes('@')) {
      showErr('emailErr', 'Valid email required.'); valid = false;
    } else clearErr('emailErr');

    if (!event) {
      showErr('eventErr', 'Please select an event.'); valid = false;
    } else clearErr('eventErr');

    if (!valid) return;

    const feedbackEl = document.getElementById('formFeedback');
    feedbackEl.textContent = '⏳ Submitting…';

    const res = await postRegistration({ name, email, event });
    feedbackEl.textContent = res.ok ? `✅ ${res.message}` : '❌ Failed. Try again.';

    if (res.ok) {
      form.reset();
      console.log('Form submitted', { name, email, event });
    }
  });
});

function showErr(id, msg) {
  const el = document.getElementById(id);
  if (el) el.textContent = msg;
  // also mark input red
  const inputMap = { nameErr: 'rName', emailErr: 'rEmail', eventErr: 'rEvent' };
  const inp = document.getElementById(inputMap[id]);
  if (inp) inp.classList.add('error');
}
function clearErr(id) {
  const el = document.getElementById(id);
  if (el) el.textContent = '';
  const inputMap = { nameErr: 'rName', emailErr: 'rEmail', eventErr: 'rEvent' };
  const inp = document.getElementById(inputMap[id]);
  if (inp) inp.classList.remove('error');
}


// ---------------------------------------------------------------
// Ex 13: Debugging – console techniques
// ---------------------------------------------------------------
function debugLog(label, data) {
  console.group(`🐞 Debug: ${label}`);
  console.log(data);
  console.groupEnd();
}
// debugLog('rawEvents', rawEvents);  // uncomment to use


// ---------------------------------------------------------------
// Ex 14: jQuery
// ---------------------------------------------------------------
$(document).ready(function () {
  // Ex 14: click handler via jQuery
  $('#registerBtn').click(function () {
    $('#jqCard').fadeIn(400);
  });
  $('#hideBtn').click(function () {
    $('#jqCard').fadeOut(400);
  });
  console.log('jQuery loaded. Version:', $.fn.jquery);
  // React/Vue note: frameworks like React/Vue provide component-based
  // architecture, virtual DOM diffing, and state management, making
  // large-scale UIs far more maintainable than jQuery.
});


// ---------------------------------------------------------------
// Initial page load (called from window.load)
// ---------------------------------------------------------------
function loadPage() {
  renderEvents(getFilteredList());
  console.log('Page initialized. Valid events:', getValidEvents(rawEvents).length);
}
