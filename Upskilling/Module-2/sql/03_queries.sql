USE community_portal;

-- Q1. User Upcoming Events
-- Show upcoming events a user is registered for in their city, sorted by date.

SELECT e.title, e.city, e.start_date
FROM Events e
JOIN Registrations r ON e.event_id = r.event_id
JOIN Users u ON r.user_id = u.user_id
WHERE e.status = 'upcoming'
  AND e.city = u.city
ORDER BY e.start_date;


-- Q2. Top Rated Events
-- Events with highest average rating (minimum 10 feedbacks).

SELECT e.title, AVG(f.rating) AS avg_rating, COUNT(f.feedback_id) AS total_feedback
FROM Events e
JOIN Feedback f ON e.event_id = f.event_id
GROUP BY e.event_id, e.title
HAVING COUNT(f.feedback_id) >= 10
ORDER BY avg_rating DESC;


-- Q3. Inactive Users
-- Users who have not registered for any event in the last 90 days.

SELECT u.user_id, u.full_name, u.email
FROM Users u
WHERE u.user_id NOT IN (
    SELECT DISTINCT user_id
    FROM Registrations
    WHERE registration_date >= DATE_SUB(CURDATE(), INTERVAL 90 DAY)
);


-- Q4. Peak Session Hours
-- Count sessions scheduled between 10 AM and 12 PM for each event.

SELECT e.title, COUNT(s.session_id) AS session_count
FROM Events e
JOIN Sessions s ON e.event_id = s.event_id
WHERE TIME(s.start_time) >= '10:00:00'
  AND TIME(s.start_time) < '12:00:00'
GROUP BY e.event_id, e.title;


-- Q5. Most Active Cities
-- Top 5 cities with the highest number of distinct user registrations.

SELECT u.city, COUNT(DISTINCT r.user_id) AS total_users
FROM Users u
JOIN Registrations r ON u.user_id = r.user_id
GROUP BY u.city
ORDER BY total_users DESC
LIMIT 5;


-- Q6. Event Resource Summary
-- Number of PDFs, images, and links uploaded per event.

SELECT e.title,
       SUM(CASE WHEN r.resource_type = 'pdf'   THEN 1 ELSE 0 END) AS pdfs,
       SUM(CASE WHEN r.resource_type = 'image' THEN 1 ELSE 0 END) AS images,
       SUM(CASE WHEN r.resource_type = 'link'  THEN 1 ELSE 0 END) AS links,
       COUNT(r.resource_id) AS total_resources
FROM Events e
LEFT JOIN Resources r ON e.event_id = r.event_id
GROUP BY e.event_id, e.title;


-- Q7. Low Feedback Alerts
-- Users who gave a rating less than 3, with their comments and event name.

SELECT u.full_name, f.rating, f.comments, e.title AS event_name
FROM Feedback f
JOIN Users u ON f.user_id = u.user_id
JOIN Events e ON f.event_id = e.event_id
WHERE f.rating < 3;


-- Q8. Sessions per Upcoming Event
-- Upcoming events with the count of sessions scheduled.

SELECT e.title, COUNT(s.session_id) AS session_count
FROM Events e
LEFT JOIN Sessions s ON e.event_id = s.event_id
WHERE e.status = 'upcoming'
GROUP BY e.event_id, e.title;


-- Q9. Organizer Event Summary
-- For each organizer, count events grouped by status.

SELECT u.full_name AS organizer,
       e.status,
       COUNT(e.event_id) AS event_count
FROM Users u
JOIN Events e ON u.user_id = e.organizer_id
GROUP BY u.user_id, u.full_name, e.status
ORDER BY u.full_name;


-- Q10. Feedback Gap
-- Events that had registrations but received no feedback.

SELECT e.title
FROM Events e
JOIN Registrations r ON e.event_id = r.event_id
WHERE e.event_id NOT IN (
    SELECT DISTINCT event_id FROM Feedback
)
GROUP BY e.event_id, e.title;


-- Q11. Daily New User Count
-- Number of users who registered each day in the last 7 days.

SELECT registration_date, COUNT(user_id) AS new_users
FROM Users
WHERE registration_date >= DATE_SUB(CURDATE(), INTERVAL 7 DAY)
GROUP BY registration_date
ORDER BY registration_date;


-- Q12. Event with Maximum Sessions
-- Event(s) with the highest number of sessions.

SELECT e.title, COUNT(s.session_id) AS session_count
FROM Events e
JOIN Sessions s ON e.event_id = s.event_id
GROUP BY e.event_id, e.title
HAVING COUNT(s.session_id) = (
    SELECT MAX(cnt) FROM (
        SELECT COUNT(session_id) AS cnt
        FROM Sessions
        GROUP BY event_id
    ) AS counts
);


-- Q13. Average Rating per City
-- Average feedback rating of events held in each city.

SELECT e.city, AVG(f.rating) AS avg_rating
FROM Events e
JOIN Feedback f ON e.event_id = f.event_id
GROUP BY e.city;


-- Q14. Most Registered Events
-- Top 3 events by total registrations.

SELECT e.title, COUNT(r.registration_id) AS total_registrations
FROM Events e
JOIN Registrations r ON e.event_id = r.event_id
GROUP BY e.event_id, e.title
ORDER BY total_registrations DESC
LIMIT 3;


-- Q15. Event Session Time Conflict
-- Sessions in the same event whose times overlap.

SELECT a.event_id,
       a.title AS session_1,
       b.title AS session_2,
       a.start_time AS s1_start,
       a.end_time   AS s1_end,
       b.start_time AS s2_start,
       b.end_time   AS s2_end
FROM Sessions a
JOIN Sessions b ON a.event_id = b.event_id
               AND a.session_id < b.session_id
WHERE a.start_time < b.end_time
  AND a.end_time   > b.start_time;


-- Q16. Unregistered Active Users
-- Users who joined in the last 30 days but haven't registered for any event.

SELECT user_id, full_name, email, registration_date
FROM Users
WHERE registration_date >= DATE_SUB(CURDATE(), INTERVAL 30 DAY)
  AND user_id NOT IN (
      SELECT DISTINCT user_id FROM Registrations
  );


-- Q17. Multi-Session Speakers
-- Speakers handling more than one session across all events.

SELECT speaker_name, COUNT(session_id) AS session_count
FROM Sessions
GROUP BY speaker_name
HAVING COUNT(session_id) > 1;


-- Q18. Resource Availability Check
-- Events with no resources uploaded.

SELECT e.title
FROM Events e
WHERE e.event_id NOT IN (
    SELECT DISTINCT event_id FROM Resources
);


-- Q19. Completed Events with Feedback Summary
-- For completed events: total registrations and average feedback rating.

SELECT e.title,
       COUNT(DISTINCT r.registration_id) AS total_registrations,
       AVG(f.rating) AS avg_rating
FROM Events e
LEFT JOIN Registrations r ON e.event_id = r.event_id
LEFT JOIN Feedback f ON e.event_id = f.event_id
WHERE e.status = 'completed'
GROUP BY e.event_id, e.title;


-- Q20. User Engagement Index
-- For each user: events attended and feedbacks submitted.

SELECT u.full_name,
       COUNT(DISTINCT r.event_id) AS events_registered,
       COUNT(DISTINCT f.feedback_id) AS feedbacks_given
FROM Users u
LEFT JOIN Registrations r ON u.user_id = r.user_id
LEFT JOIN Feedback f ON u.user_id = f.user_id
GROUP BY u.user_id, u.full_name;


-- Q21. Top Feedback Providers
-- Top 5 users who submitted the most feedback.

SELECT u.full_name, COUNT(f.feedback_id) AS feedback_count
FROM Users u
JOIN Feedback f ON u.user_id = f.user_id
GROUP BY u.user_id, u.full_name
ORDER BY feedback_count DESC
LIMIT 5;


-- Q22. Duplicate Registrations Check
-- Check if any user registered more than once for the same event.

SELECT user_id, event_id, COUNT(*) AS registration_count
FROM Registrations
GROUP BY user_id, event_id
HAVING COUNT(*) > 1;


-- Q23. Registration Trends
-- Month-wise registration count over the past 12 months.

SELECT DATE_FORMAT(registration_date, '%Y-%m') AS month,
       COUNT(*) AS registrations
FROM Registrations
WHERE registration_date >= DATE_SUB(CURDATE(), INTERVAL 12 MONTH)
GROUP BY month
ORDER BY month;


-- Q24. Average Session Duration per Event
-- Average session duration in minutes for each event.

SELECT e.title,
       AVG(TIMESTAMPDIFF(MINUTE, s.start_time, s.end_time)) AS avg_duration_minutes
FROM Events e
JOIN Sessions s ON e.event_id = s.event_id
GROUP BY e.event_id, e.title;


-- Q25. Events Without Sessions
-- Events that have no sessions scheduled.

SELECT e.title
FROM Events e
WHERE e.event_id NOT IN (
    SELECT DISTINCT event_id FROM Sessions
);
