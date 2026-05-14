import type { Participant, ParticipantRole } from '../domain/participant'

export async function addParticipant(caseId: number, partyId: number, role: ParticipantRole): Promise<Participant> {
  const response = await fetch(`/api/cases/${caseId}/participants`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ partyId, role }),
  })
  if (!response.ok) throw new Error('Could not add participant.')
  return (await response.json()) as Participant
}

export async function updateParticipant(caseId: number, partyId: number, role: ParticipantRole): Promise<Participant> {
  const response = await fetch(`/api/cases/${caseId}/participants/${partyId}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ role }),
  })
  if (!response.ok) throw new Error('Could not update participant.')
  return (await response.json()) as Participant
}

export async function removeParticipant(caseId: number, partyId: number): Promise<void> {
  const response = await fetch(`/api/cases/${caseId}/participants/${partyId}`, { method: 'DELETE' })
  if (!response.ok) throw new Error('Could not remove participant.')
}
