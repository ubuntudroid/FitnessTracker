Given(/^I have set up Week (\d+)$/) do |week_number|
  for i in 1..week_number.to_i
    week = 'Week ' + i.to_s
    touch("actionMenuItemView marked:'Add'")
    touch("textView marked:'#{week}'")
    wait_for_elements_exist("editText marked:'Weight'", :timeout => 10)
    enter_text("editText marked:'Weight'", "127.8")
    enter_text("editText marked:'Fat'", "27.4")
    enter_text("editText marked:'Water'", "49.2")
    enter_text("editText marked:'Muscle'", "31.3")
    touch("actionMenuItemView marked:'Save'")
    press_back_button
    wait_for_elements_exist("textView marked:'#{week}'", :timeout => 10)
  end
end

Then (/^I see "(.*?)" weight trend for Week (\d+)$/) do |weight_progress, week_number|
  if ['stagnant','decreasing','increasing','unknown'].include?weight_progress
    if weight_progress == 'unknown'
      content_description = 'Week ' + week_number.to_s + ': weight trend ' + weight_progress
    else
      content_description = 'Week ' + week_number.to_s + ': weight ' + weight_progress
    end
    view_with_mark_exists(content_description)
  else
    raise "Unknown weight trend attribute! Use either 'stagnant', 'decreasing', 'increasing' or 'unknown'."
  end
end